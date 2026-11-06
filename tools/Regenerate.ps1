param(
    [Parameter(Mandatory=$true)][string]$InputJar,
    [Parameter(Mandatory=$true)][string]$OutputDirectory
)
$ErrorActionPreference = 'Stop'
$InputJar = (Resolve-Path $InputJar).Path
$OutputDirectory = [IO.Path]::GetFullPath($OutputDirectory)
if (Test-Path $OutputDirectory) {
    if (@(Get-ChildItem $OutputDirectory -Force).Count -ne 0) { throw 'Use a new or empty output directory to avoid overwriting your work.' }
}
New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
$cache = Join-Path $OutputDirectory '.tool-cache'
$java = Join-Path $OutputDirectory 'src\main\java'
$resources = Join-Path $OutputDirectory 'src\main\resources'
$reports = Join-Path $OutputDirectory 'reports'
New-Item -ItemType Directory -Force -Path $cache,$java,$resources,$reports | Out-Null
foreach ($artifact in @('asm','asm-tree','asm-commons')) {
    Invoke-WebRequest "https://repo.maven.apache.org/maven2/org/ow2/asm/$artifact/9.11.1/$artifact-9.11.1.jar" -OutFile "$cache\$artifact.jar" -UseBasicParsing
}
Invoke-WebRequest 'https://github.com/Vineflower/vineflower/releases/download/1.12.0/vineflower-1.12.0.jar' -OutFile "$cache\vineflower.jar" -UseBasicParsing
Invoke-WebRequest 'https://maven.fabricmc.net/net/fabricmc/yarn/1.21.11+build.6/yarn-1.21.11+build.6-v2.jar' -OutFile "$cache\yarn.jar" -UseBasicParsing
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [IO.Compression.ZipFile]::OpenRead("$cache\yarn.jar")
try {
    $reader = [IO.StreamReader]::new($zip.GetEntry('mappings/mappings.tiny').Open())
    try { [IO.File]::WriteAllText("$cache\yarn.tiny",$reader.ReadToEnd()) } finally { $reader.Dispose() }
} finally { $zip.Dispose() }
$classpath = "$cache\asm.jar;$cache\asm-tree.jar;$cache\asm-commons.jar"
& javac -cp $classpath -d $cache "$PSScriptRoot\Deobfuscate.java" "$PSScriptRoot\ValidateRemap.java" "$PSScriptRoot\CheckJavaSyntax.java" "$PSScriptRoot\ApplyNoAuth.java" "$PSScriptRoot\TestNoAuth.java"
if ($LASTEXITCODE -ne 0) { throw 'Tool compilation failed. Use JDK 21 or later.' }
& java -Xmx2G -cp "$cache;$classpath" Deobfuscate $InputJar "$cache\yarn.tiny" "$PSScriptRoot\class-overrides.tsv" "$PSScriptRoot\member-overrides.tsv" "$cache\readable.jar" $reports
if ($LASTEXITCODE -ne 0) { throw 'Remapping failed.' }
& java -Xmx2G -cp "$cache;$classpath" ValidateRemap $InputJar "$cache\readable.jar" "$cache\yarn.tiny" "$reports\name-mappings.tsv" "$reports\pre-edit-validation.txt"
if ($LASTEXITCODE -ne 0) { throw 'Structural validation failed.' }
& java -Xmx4G -jar "$cache\vineflower.jar" --folder --threads=4 --log-level=WARN --skip-extra-files=true --use-lvt-names=false --variable-renaming=tiny --rename-parameters=true "$cache\readable.jar" $java *> "$reports\vineflower.log"
if ($LASTEXITCODE -ne 0) { throw 'Decompilation failed.' }
& java -cp $cache ApplyNoAuth $OutputDirectory $PSScriptRoot
if ($LASTEXITCODE -ne 0) { throw 'Source-only activation removal failed.' }
& java -Xmx2G -cp $cache CheckJavaSyntax $java "$reports\java-syntax.txt"
if ($LASTEXITCODE -ne 0) { throw 'Recovered Java has syntax errors.' }
& java -cp $cache TestNoAuth $OutputDirectory "$cache\isolated-tests"
if ($LASTEXITCODE -ne 0) { throw 'Isolated behavior checks failed.' }
$zip = [IO.Compression.ZipFile]::OpenRead("$cache\readable.jar")
try {
    foreach ($entry in $zip.Entries) {
        if ($entry.FullName.EndsWith('/') -or $entry.FullName.EndsWith('.class')) { continue }
        $path = [IO.Path]::GetFullPath((Join-Path $resources $entry.FullName))
        if (-not $path.StartsWith($resources + [IO.Path]::DirectorySeparatorChar,[StringComparison]::OrdinalIgnoreCase)) { throw 'Unsafe archive path.' }
        New-Item -ItemType Directory -Force -Path (Split-Path $path) | Out-Null
        [IO.Compression.ZipFileExtensions]::ExtractToFile($entry,$path,$false)
    }
} finally { $zip.Dispose() }
Write-Output "Readable source saved to $OutputDirectory. This is not a verified buildable mod."
