(ns front.results-section)

(defn results-section []
  [:div {:id "results" :class "flex h-screen w-screen items-center justify-center"}
   [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}

    ;; Título
    [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"}
     "Resultados das Últimas Operações"]

    ;; Lista de resultados
    [:ul {:class "text-center mt-3 p-3 flex flex-col gap-3"}
     [:li {:class "font-medium text-emerald-600"} "+ 230 calorias de Hambúrguer"]
     [:li {:class "font-medium text-rose-600"} "- 100 calorias de 15 minutos Funcionando"]]

    ;; Linha divisória
    [:hr {:class "border-2"}]

    ;; Total
    [:h2 {:class "text-center p-3 text-slate-700"}
     [:span {:class "font-bold text-lg text-indigo-700"} "Total: "]
     [:span {:class "font-semibold"} "1500 "] "calorias"]]])
