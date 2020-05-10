(ns uno.render
  (:require
    [uno.render.html :as html]
    [uno.model :as model]
    [crate.core :as crate]))

(defn id [x]
  (js/document.getElementById x))

(defn replace-with [k body]
  (.replaceWith (id k) body))

(defn render-html []
  (replace-with
    "content"
    (crate/html
      (html/card
        {:color "red"
         :number 3}))))
