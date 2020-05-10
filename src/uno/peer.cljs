(ns uno.peer)

(set! *warn-on-infer* true)

(def peer (atom nil))
(def connections (atom {}))

(defn- salt-id [id]
  (str id "-uno"))
(defn- peer-id [^js/conn conn]
  (-> conn .-peer (.replace "-uno" "")))

(defn- listen [^js/peer peer handler]
  (.on peer "connection"
       (fn [^js/conn conn]
         (.on conn "data" #(->> % js->clj (handler (peer-id conn)))))))

(defn set-peer! [id handler]
  (reset! peer (js/Peer. (salt-id id) #js {:debug 1}))
  (listen @peer handler))

(defn send-to-connection [^js/conn conn content]
  (.send conn (clj->js content)))

(defn send-to-peer [id content]
  (let [id (salt-id id)
        ^js/peer peer @peer]
    (if-let [conn (@connections id)]
      (send-to-connection conn content)
      (let [^js/conn conn (.connect peer id)]
        (.on conn "open" #(send-to-connection conn content))
        (.on conn "close" #(swap! connections dissoc id))
        (swap! connections assoc id conn)))))
