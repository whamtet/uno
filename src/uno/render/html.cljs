(ns uno.render.html
  (:require
    [uno.util :refer [format]]))

(defn- card-style [color]
  (format "border: 1px solid #aaa;
          color: %s;
          width: 2em;
          height: 2em;
          display: flex;
          justify-content: center;
          align-items: center;" color))

(defn card [{:keys [color number] :as card}]
  [:div {:onclick #(js/uno.event.put-down card)
         :style (format "color: %s;" color)}
   number])

(defn hand [cards]
  [:div.flex-container (map card cards)])

(defn table [stack]
  (when-let [{:keys [color number]} (peek stack)]
    [:div {:style "display: flex; justify-content: center;"}
     [:div {:style (card-style color)
            :onclick js/uno.event.pickup-table}
      number]]))

(defn screen [{:keys [stack]} cards]
  [:div#content
   (table stack)
   (hand cards)
   [:div
    [:button {:type "button"
              :onclick js/uno.event.pickup}
     "Pickup"]]])
