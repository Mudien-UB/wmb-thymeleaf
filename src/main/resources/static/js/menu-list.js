import { rupiahFormat } from './rupiah-format.js';

document.addEventListener('DOMContentLoaded', () => {

    // auto submit untuk pencarian menu dengan nama
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        const form = searchInput.form;
        let debounceTimer = null;

        searchInput.addEventListener('input', () => {
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => {
                form.submit();
            }, 500);
        });
    }

    // pemformatan price/harga dalam tampilan ke rupiah
    const priceCells = document.querySelectorAll('td[data-price]');
    priceCells.forEach(cell => {
        const rawValue = parseFloat(cell.textContent.replace(/[^\d.,]/g, '').replace(',', '.')) || 0;
        cell.textContent = rupiahFormat(rawValue);
    });

    // modal untuk konfirmasi untuk hapus menu
    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');
    document.querySelectorAll('.delete-button button').forEach(button => {
        button.addEventListener('click', () => {
            const form = button.closest('form');
            const action = form.getAttribute('data-action');
            deleteModal.classList.remove('hidden');
            deleteForm.setAttribute('action', action);
        });
    });

});


