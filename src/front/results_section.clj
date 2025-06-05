(ns front.results-section
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn results-section []
  (let [alimentos (-> (http/get "http://localhost:3000/listar/alimentos" {:as :json})
                      :body)
        exercicios (-> (http/get "http://localhost:3000/listar/exercicios" {:as :json})
                       :body)
        total (- (reduce + 0 (map :calorias alimentos))
                 (reduce + 0 (map :calorias exercicios))) 
        itens (concat
               (map (fn [{:keys [alimento calorias]}]
                      [:li {:class "font-medium text-emerald-600"}
                       (str "+ " (int calorias) " calorias de " alimento)])
                    alimentos)
               (map (fn [{:keys [exercicio calorias]}]
                      [:li {:class "font-medium text-rose-600"}
                       (str "- " (int calorias) " calorias de " exercicio)])
                    exercicios))
        total-formatado (format "%d" (int total))
        ]
    [:div {:id "results" :class "flex h-screen w-screen items-center justify-center"}
     [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}

      ;; Título
      [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"}
       "Resultados das 5 Últimas Operações"]

      ;; Lista de resultados
      [:ul {:class "text-center mt-3 p-3 flex flex-col gap-3"}
       (doall itens)]

      ;; Linha divisória
      [:hr]

      ;; Total
      [:h2 {:class "text-center p-3 text-slate-700"}
       [:span {:class "font-bold text-lg text-indigo-700"} "Total: "]
       [:span {:class "font-semibold"} total-formatado] " calorias"]]]))
