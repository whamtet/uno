(ns uno.core
  (:require
    uno.event
    [uno.render :as render]
    [uno.state :as state]
    [uno.msg :as msg]
    [uno.model :as model]))

(enable-console-print!)

(defn new-game []
  (model/pickup-many!)
  (render/render-html))

(defn main []
  (let [leader (js/confirm "Làm chủ không?")]
    (when-let [username (js/prompt "Chủ tên gì?")]
      (msg/set-peer! (when leader username))
      (if leader
        (new-game)
        (msg/request-state username)))))

(main)
