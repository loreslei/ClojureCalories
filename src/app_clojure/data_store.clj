(ns app-clojure.data-store
  (:require [clj-http.client :as http]
            [cheshire.core :as json])) ; Importe cheshire.core para parsear JSON

(def alimentos-atom (atom []))
(def exercicios-atom (atom []))

(defn carregar-dados-iniciais! []
  "Carrega os dados iniciais dos endpoints da API e os armazena nos átomos.
   Isso evita que os átomos comecem vazios a cada reinício da aplicação."
  (try
    (reset! alimentos-atom (-> (http/get "http://localhost:3000/listar/alimentos")
                               :body
                               (json/parse-string true))) ; Use json/parse-string para converter JSON para Clojure map
    (reset! exercicios-atom (-> (http/get "http://localhost:3000/listar/exercicios")
                                :body
                                (json/parse-string true))) ; Use json/parse-string
    (println "Dados iniciais carregados nos átomos.")
    (catch Exception e
      (println (str "Erro ao carregar dados iniciais: " (.getMessage e)))
      (.printStackTrace e))))

;; **Importante:** Chame a função para carregar os dados quando o namespace for carregado.
;; Se você tiver um mecanismo de persistência real (ex: banco de dados), esta chamada
;; pode ser ajustada para ler do DB na inicialização.
(carregar-dados-iniciais!)