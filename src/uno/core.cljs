(ns uno.core
  (:require
    uno.event
    [uno.render :as render]
    [uno.state :as state]
    [uno.msg :as msg]
    [uno.model :as model]))

(enable-console-print!)

(defn new-game [username]
  (state/set-username username)
  (model/pickup-many!)
  (render/render-html))

(defn main []
  (let [leader (js/confirm "Làm chủ không?")
        prompt-msg (if leader "Chủ tên gì?" "Kết với chủ khác tên gì?")]
    (when-let [username (js/prompt prompt-msg)]
      (msg/set-peer! (when leader (.toLowerCase username)))
      (if leader
        (new-game username)
        (msg/request-state (.toLowerCase username))))))

(main)
