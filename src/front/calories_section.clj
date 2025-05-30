(ns front.calories-section)

(defn calories-section []
  [:div {:id "calories"
         :class "flex h-screen mt-20 w-screen items-center justify-center"}
   [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}
    [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"}
     "Registrar Alimentos ou Exercícios"]
    [:div {:class "my-5 flex flex-col gap-4"}

     ;; Alimento
     [:div
      [:p {:class "p-2 text-slate-600 font-semibold"} "Alimento"]
      [:div {:class "flex items-center"}
       [:div {:class "h-10 w-11 rounded-l-xl border-l border-t border-b border-slate-300 p-2 flex items-center justify-center"}
        [:i {:class "fa-solid fa-burger text-slate-700"}]]
       [:input {:class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"
                :placeholder "Nome do Alimento e Porção"}]]]

     ;; Exercício
     [:div
      [:p {:class "p-2 text-slate-600 font-semibold"} "Exercício"]
      [:div {:class "flex items-center"}
       [:div {:class "h-10 w-11 rounded-l-xl border-l border-t border-b border-slate-300 p-2 flex items-center justify-center"}
        [:i {:class "fa-solid fa-dumbbell text-slate-700"}]]
       [:input {:class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"
                :placeholder "Tipo de Exercício e Duração"}]]]

     ;; Botão Enviar
     [:button {:type "submit"
               :class "transition-all mt-3 h-10 cursor-pointer rounded-xl bg-indigo-500 p-2 font-semibold text-white duration-700 hover:bg-indigo-600"}
      "Enviar"]]]])