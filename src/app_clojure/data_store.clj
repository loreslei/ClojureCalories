(ns app-clojure.data-store
  ;(:require [clj-http.client :as http]
           ; [cheshire.core :as json])
  )

(def alimentos-atom (atom []))
(def exercicios-atom (atom []))
;; armazenar as datas do filtro
(def filtro-datas-atom (atom {:dataInicio nil :dataFim nil}))

;; (defn carregar-dados-iniciais! []
;;   ;"Carrega os dados iniciais dos endpoints da API e os armazena nos átomos."
;;   (try
;;     (reset! alimentos-atom (-> (http/get "http://localhost:3000/listar/alimentos")
;;                                :body
;;                                (json/parse-string true)))
;;     (reset! exercicios-atom (-> (http/get "http://localhost:3000/listar/exercicios")
;;                                 :body
;;                                 (json/parse-string true)))
;;     ;;(println "Dados iniciais carregados nos átomos.")
;;     (catch Exception e
;;       (println (str "Erro ao carregar dados iniciais: " (.getMessage e)))
;;       (.printStackTrace e))))

;; (carregar-dados-iniciais!)