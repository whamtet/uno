(ns uno.event
  (:require
    [uno.render :as render]
    [uno.model :as model]))

(defn ^:export pickup []
  (model/pickup!)
  (render/render-html))

(defn ^:export pickup-table []
  (model/pickup-table!)
  (render/render-html))

(defn ^:export put-down [card]
  (model/put-down! card)
  (render/render-html))
