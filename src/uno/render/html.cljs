(ns uno.render.html
  (:require
    [uno.util :refer [format]]))

(defn- card-style [i color]
  (format "border: 1px solid #aaa;
          color: %s;
          opacity: %s;
          width: 2em;
          height: 2em;
          display: flex;
          justify-content: center;
          align-items: center;" color (js/Math.pow (- 1 (/ i 3)) 2)))

(defn- disp-number [number]
  (if (number? number)
    (mod number 10)
    ({"S2" "S" "R2" "R"} number number)))

(defn card [{:keys [color number] :as card}]
  [:div {:onclick #(js/uno.event.put-down card)
         :style (format "color: %s;" (if (= "P4" number) "black" color))}
   (disp-number number)])

(defn hand [cards]
  [:div.flex-container (map card cards)])

(defn card-table [i {:keys [color number]}]
  [:div {:style (card-style i (if (= "P4" number) "black" color))
         :onclick js/uno.event.pickup-table}
   (disp-number number)])

(defn table [stack]
  [:div {:style "display: flex; justify-content: center;"}
   (map-indexed card-table (reverse (take-last 3 stack)))])

(defn screen [username {:keys [stack]} cards]
  [:div#content
   (when username
     [:div username])
   (table stack)
   (hand cards)
   [:div
    [:button {:type "button"
              :onclick js/uno.event.pickup}
     "Pickup"]]
   [:br]
   [:div
    [:button {:type "button"
              :onclick js/uno.event.restart}
     "Restart"]]])
