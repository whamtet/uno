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

(defn prompt [s]
  (some-> s js/prompt .trim not-empty))

(defn main []
  (when-let [name (prompt "Bạn tên gì?")]
    (state/set-username name)
    (msg/set-peer! (.toLowerCase name))
    (if (js/confirm "Làm chủ không?")
      (new-game)
      (when-let [chu (prompt "Kết với chủ khác tên gì?")]
        (msg/request-state (.toLowerCase chu))))))

(main)
