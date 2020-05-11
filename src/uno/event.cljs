(ns uno.event
  (:require
    [uno.render :as render]
    [uno.msg :as msg]
    [uno.state :as state]
    [uno.model :as model]))

(defn ^:export pickup []
  (when (not-empty (state/get-hand))
    (model/pickup!)
    (msg/notify-peers)
    (render/render-html)))

(defn ^:export pickup-table []
  (model/pickup-table!)
  (msg/notify-peers)
  (render/render-html))

(defn ^:export put-down [card]
  (model/put-down! card)
  (msg/notify-peers)
  (render/render-html))

(defn ^:export restart []
  (when (js/confirm "Restart?")
    (model/restart!)
    (msg/notify-peers)
    (render/render-html)))
