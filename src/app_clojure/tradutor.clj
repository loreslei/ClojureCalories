(ns app-clojure.tradutor
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [clojure.data.json :as json]))



(defn capitalizar [texto]

  (let [primeira (subs texto 0 1)
        resto (subs texto 1)]
    (str (.toUpperCase primeira) (.toLowerCase resto))))

(defn traduzir-pt-en [texto]
  (let [url (str "https://api.mymemory.translated.net/get?q=" (java.net.URLEncoder/encode (capitalizar texto) "UTF-8") "&langpair=pt|en")
        response (client/get url {:accept :json})]

    (let [data (json/read-str (:body response) :key-fn keyword)
          resultado (get-in data [:responseData :translatedText])]

      resultado)))

;; (defn traduzir-en-pt [texto]
;;   (let [url (str "https://api.mymemory.translated.net/get?q=" (java.net.URLEncoder/encode (capitalizar texto) "UTF-8") "&langpair=en|pt")
;;         response (client/get url {:accept :json})
;;         data (json/read-str (:body response) :key-fn keyword)
;;         resultado (get-in data [:responseData :translatedText])] 
;;     resultado))