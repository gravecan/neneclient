package aethereal.lib.jsoup;

import java.util.function.Consumer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class JsoupComponent139 {
   private final Element delegate;

   JsoupComponent139(Element element) {
      this.delegate = element;
   }

   public String getDelegate_1298() {
      return this.delegate.text();
   }

   public String getDelegate_1299() {
      return this.delegate.ownText();
   }

   public String getDelegate_1297() {
      return this.delegate.text();
   }

   public String computeValue_1304(String string) {
      return this.delegate.attr(string);
   }

   public boolean checkCondition_1300(String string) {
      return this.delegate.hasAttr(string);
   }

   public JsoupComponent139 computeValue_1301(String string) {
      Element element = this.delegate.selectFirst(string);
      return element == null ? null : new JsoupComponent139(element);
   }

   public JsoupComponent139 computeValue_1296(String string) {
      return this.computeValue_1301(string);
   }

   public Elements computeValue_1302(String string) {
      return this.delegate.select(string);
   }

   public void performAction_1303(Consumer<? super Element> consumer) {
      this.delegate.select("input[type=hidden]").forEach(consumer);
   }
}
