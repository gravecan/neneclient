package aethereal.lib.jsoup;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class JsoupComponent138 {
   private final Document delegate;

   JsoupComponent138(Document document) {
      this.delegate = document;
   }

   public JsoupComponent139 computeValue_1294(String string) {
      Element element = this.delegate.selectFirst(string);
      return element == null ? null : new JsoupComponent139(element);
   }

   public List<JsoupComponent139> computeValue_1291(String string) {
      Elements elements = this.delegate.select(string);
      ArrayList arrayList = new ArrayList(elements.size());

      for (Element element : elements) {
         arrayList.add(new JsoupComponent139(element));
      }

      return arrayList;
   }

   public JsoupComponent138 computeValue_1292() {
      return this;
   }

   public Element getDelegate_1293() {
      return this.delegate.body();
   }

   public String computeValue_1295(String string) {
      Element element = this.delegate.body();
      return element == null ? "" : element.attr(string);
   }
}
