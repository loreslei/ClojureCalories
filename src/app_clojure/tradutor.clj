(ns app-clojure.tradutor
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as str]
            )
  (:import [java.net URLEncoder]
           [java.text Normalizer Normalizer$Form]))


;; (defn limpar-texto [texto]
;;   (println "Texto bruto traduzido:" texto)
;;   (println "Bytes:" (map int texto)) ; debug para mostrar os códigos ASCII

;;   (let [limpo (-> texto
;;                   (Normalizer/normalize Normalizer$Form/NFD)
;;                   (str/replace #"[^\p{ASCII}]" "") ; remove acentos
;;                   (str/replace #"[\p{Cntrl}]" "") ; remove caracteres de controle
;;                   (str/replace #"[\p{Zs}]" " ")   ; normaliza espaços
;;                   (str/trim))]
;;     (println "Texto sanitizado:" limpo)
;;     limpo))

(defn capitalizar [s]
  (if (str/blank? s) ;; Check if s is nil or empty/whitespace
    "" ;; Return an empty string or handle as appropriate for your application
    (str (str/upper-case (subs s 0 1)) (subs s 1))))

(defn traduzir-pt-en [texto]
  (let [url (str "https://api.mymemory.translated.net/get?q=" (java.net.URLEncoder/encode (capitalizar texto) "UTF-8") "&langpair=pt|en")
        response (client/get url {:accept :json})]
    ;; (println "URL da MyMemory:" url) ; Adicione esta linha para verificar a URL
    ;; (println "Resposta RAW da MyMemory:" (:body response)) ; **Adicione esta linha para imprimir a resposta completa**
    (let [data (json/read-str (:body response) :key-fn keyword)
          resultado (get-in data [:responseData :translatedText])]
      ;; (println "11111111")
      ;; (println "Resultado traduzido (antes de limpar):" resultado)
      ;; (limpar-texto resultado)
      resultado)))

(defn traduzir-en-pt [texto]
  (let [url (str "https://api.mymemory.translated.net/get?q=" (java.net.URLEncoder/encode (capitalizar texto) "UTF-8") "&langpair=en|pt")
        response (client/get url {:accept :json})
        data (json/read-str (:body response) :key-fn keyword)
        resultado (get-in data [:responseData :translatedText])]
    ;; (limpar-texto resultado)
    resultado))