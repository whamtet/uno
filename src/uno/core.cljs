(ns uno.core
  (:require
    uno.event
    [uno.render :as render]
    [uno.state :as state]
    [uno.msg :as msg]
    [uno.model :as model]))

(enable-console-print!)

(defn prompt [s]
  (loop [result nil]
    (or result (recur (js/prompt s)))))

(defn new-game []
  (state/new-game!)
  (model/pickup-many! 7)
  (render/render-html))

(defn main []
  (let [username (prompt "Tên của bạn")]
    (msg/set-peer! username))
  (if-let [existing-game (js/prompt "Kết với bạn nào?")]
    (msg/request-state existing-game)
    (new-game)))

(main)
