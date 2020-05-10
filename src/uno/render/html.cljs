(ns uno.render.html
  (:require
    [uno.util :refer [format]]))

(defn card [{:keys [color number]}]
  [:div {:style
         (format "border: 1px solid gray;
                 color: %s;
                 width: 1.5em;
                 height: 2em;
                 display: flex;
                 justify-content: center;
                 align-items: center;" color)}
   number])
