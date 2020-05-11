(ns uno.render
  (:require
    [uno.render.html :as html]
    [uno.state :as state]
    [crate.core :as crate]))

(defn id [x]
  (js/document.getElementById x))

(defn replace-with [k body]
  (.replaceWith (id k) body))

(defn render-html []
  (replace-with
    "content"
    (crate/html
      (html/screen
        (state/get-username)
        (state/get-game-state)
        (state/get-hand)))))
