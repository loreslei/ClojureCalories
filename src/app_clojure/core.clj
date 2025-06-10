(ns app-clojure.core
  (:require
   [compojure.core :refer :all]
   [ring.adapter.jetty :refer [run-jetty]]
   [app-clojure.handler :refer [app]])
  (:gen-class))


(defn -main [& _]
  (run-jetty app {:port 3000 :join? false}))