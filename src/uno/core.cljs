(ns uno.core
  (:require
    uno.event
    [uno.render :as render]
    [uno.state :as state]
    [uno.msg :as msg]
    [uno.model :as model]))

(enable-console-print!)

(defn new-game []
  (state/new-game!)
  (model/pickup-many! 7)
  (render/render-html))

(defn main []
  (when-let [username (js/prompt "Tên của bạn")]
    (msg/set-peer! username)
    (if-let [existing-game (js/prompt "Kết với bạn nào?")]
      (msg/request-state existing-game)
      (new-game))))

(main)
