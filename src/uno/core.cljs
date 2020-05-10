(ns uno.core
  (:require
    [uno.render :as render]
    [uno.state :as state]
    [uno.msg :as msg]
    [uno.model :as model]))

(enable-console-print!)

(defn prompt [s]
  (loop [result nil]
    (or result (recur (js/prompt s)))))

(defn prompt-or-deny [s1 s2]
  (if (js/confirm s1)
    nil
    (or (js/prompt s2) (recur s1 s2))))

(defn main []
  (let [username "Matt" #_(prompt "Tên của bạn")]
    (state/set-username username)
    (msg/set-peer! username))
  (if-let [existing-game nil #_(prompt-or-deny "Bắt đàu trò chơi mới?" "Kết với bạn nào?")]
    (println "connect with" existing-game)
    (render/render-html)))

(main)
