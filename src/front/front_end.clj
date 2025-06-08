(ns front.front-end
  (:require
   [front.calories-section :refer [calories-section]]
   [front.final-table :refer [final-table]]
   [front.results-section :refer [results-section]]
   [front.navbar :refer [navbar]]
   [front.user-section :refer [user-section]]
   [hiccup.page :refer [html5]]))


(defn home-page [operacoes-para-exibir] ; Agora recebe os dados para exibir
  (html5 {:class "scroll-smooth"}
         {:lang "pt-br"}
         [:head
          [:meta {:charset "UTF-8"}]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
          [:title "Clojure Calories"]
          [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
          [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin "true"}]
          [:link {:href "https://fonts.googleapis.com/css2?family=Montserrat&display=swap" :rel "stylesheet"}]
          [:script {:src "https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"}]
          [:link {:rel "icon" :href "assets/logo.png"}]
          [:script {:src "https://kit.fontawesome.com/16fe6351cd.js" :crossorigin "anonymous"}]
          [:style "
        /* CHECKBOX TOGGLE SWITCH */
        /* @apply rules for documentation, these do not work as inline style */
        .toggle-checkbox:checked {
            right: 0;
            border-color: #68D391;
        }
    
        .toggle-checkbox:checked+.toggle-label {
            background-color: #68D391;
        }
    
        body {
            font-family: \"Montserrat\", sans-serif !important;
        }
      "]]
         [:body.bg-slate-100.overflow-x-hidden
          (navbar)
          (user-section)
          (calories-section)
          (results-section)
          (final-table operacoes-para-exibir) ; Passa os dados recebidos para final-table
          [:script
           "window.addEventListener('DOMContentLoaded', () => {
                const toggleElements = () => {
        document.getElementById('MenuOculto').classList.toggle('hidden');
        document.getElementById('IconBars').classList.toggle('hidden');
        document.getElementById('IconClose').classList.toggle('hidden');
    };
    
    const changeType = () => {
    document.getElementById('Food').classList.toggle('hidden');
    document.getElementById('Exercise').classList.toggle('hidden');

    document.getElementById('foodLabel').classList.toggle('font-bold');
    document.getElementById('exerciseLabel').classList.toggle('font-bold');

    document.getElementById('formAlimento').classList.toggle('hidden');
    document.getElementById('formExercicio').classList.toggle('hidden');

    
};
    
    document.getElementById('Menu').onclick = toggleElements;
    
    document.getElementById('Switch').addEventListener('change', changeType);
    
    ['close1', 'close2', 'close3'].forEach(id => {
        document.getElementById(id).onclick = toggleElements;
    }); 
    
    const rowsPerPage = 5;
    let currentPage = 1;
    
    const tbody = document.querySelector('tbody');
    const rows = Array.from(tbody ? tbody.querySelectorAll('tr') : []); 
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    
    const paginationContainer = document.getElementById('pagination');
    
    function renderRows() {
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
    
        rows.forEach((row, i) => {
            row.style.display = i >= start && i < end ? '' : 'none';
        });
    
        const resultInfo = document.querySelector('.text-sm.text-slate-500 b');
        if (resultInfo) {
            resultInfo.innerText = `${start + 1}-${Math.min(end, rows.length)}`;
        }
    }
    
    function updatePaginationButtons() {
        if (!paginationContainer) return; 
        paginationContainer.querySelectorAll('.page-btn').forEach(btn => btn.remove());
    
        const nextBtn = document.getElementById('nextBtn');
    
        let startPage = Math.max(1, Math.min(currentPage - 1, totalPages - 2));
        let endPage = Math.min(startPage + 2, totalPages);
    
        if (endPage - startPage < 2) {
            startPage = Math.max(1, endPage - 2);
        }
    
        for (let i = startPage; i <= endPage; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.className =
                `page-btn px-3 py-1 min-w-9 min-h-9 text-sm font-normal rounded transition duration-200 ease ${
                i === currentPage
                    ? 'text-white bg-slate-800 border border-slate-800'
                    : 'text-slate-500 bg-white border border-slate-200 hover:bg-slate-50 hover:border-slate-400'
                }`;
            btn.dataset.page = i;
    
            paginationContainer.insertBefore(btn, nextBtn);
        }
    
        const prevBtn = document.getElementById('prevBtn');
        if (prevBtn) prevBtn.disabled = currentPage === 1;
        if (nextBtn) nextBtn.disabled = currentPage === totalPages;
        const totalPagesSpan = document.getElementById('totalPages');
        if (totalPagesSpan) totalPagesSpan.innerHTML = totalPages;
    }
    
    function showPage(page) {
        currentPage = page;
        renderRows();
        updatePaginationButtons();
    }
    
    const prevBtn = document.getElementById('prevBtn');
    if (prevBtn) {
        prevBtn.addEventListener('click', () => {
            if (currentPage > 1) {
                showPage(currentPage - 1);
            }
        });
    }

    const nextBtn = document.getElementById('nextBtn');
    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
            if (currentPage < totalPages) {
                showPage(currentPage + 1);
            }
        });
    }
    
    if (paginationContainer) {
        paginationContainer.addEventListener('click', (e) => {
            if (e.target.classList.contains('page-btn')) {
                const page = parseInt(e.target.dataset.page);
                showPage(page);
            }
        });
    }
    
    renderRows();
    updatePaginationButtons();

            });"
          ]]))