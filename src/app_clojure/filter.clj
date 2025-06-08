(ns app-clojure.filter
  (:require [clj-time.format :as f]
            [app-clojure.data-store :refer [alimentos-atom exercicios-atom filtro-datas-atom]])) ; <--- Importe o novo átomo

(defn filtrar-por-data [operacoes data-inicio-str data-fim-str]
  "Filtra uma lista de operações por um intervalo de datas.
   data-inicio-str e data-fim-str devem estar no formato 'yyyy-MM-dd' (do input HTML).
   A dataAdicao dos itens deve estar no formato 'dd-MM-yyyy'."
  (let [formatter-input (f/formatter "yyyy-MM-dd") ; Formato esperado do input HTML
        formatter-data-adicao (f/formatter "dd-MM-yyyy") ; Formato em que dataAdicao está nos seus dados

        data-inicio (if (and data-inicio-str (not (empty? data-inicio-str)))
                      (f/parse formatter-input data-inicio-str)
                      nil)
        data-fim (if (and data-fim-str (not (empty? data-fim-str)))
                   (f/parse formatter-input data-fim-str)
                   nil)]

    (filter (fn [item]
              (let [data-item-str (get item :dataAdicao)
                    data-item (if (and data-item-str (not (empty? data-item-str)))
                                (f/parse formatter-data-adicao data-item-str)
                                nil)]
                (and (or (nil? data-inicio)
                         (and data-item (>= (compare data-item data-inicio) 0)))
                     (or (nil? data-fim)
                         (and data-item (<= (compare data-item data-fim) 0))))))
            operacoes)))

(defn filtrar-data [req]
  (let [data-inicio (get-in req [:params :dataInicio])
        data-fim (get-in req [:params :dataFim])
        todas-operacoes (sort-by :dataAdicao (concat @alimentos-atom @exercicios-atom))]

    ;; Atualiza o átomo filtro-datas-atom com as datas da consulta
    (reset! filtro-datas-atom {:dataInicio data-inicio :dataFim data-fim})

    (filtrar-por-data todas-operacoes data-inicio data-fim)))