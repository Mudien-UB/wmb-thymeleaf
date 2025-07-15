import { formatRupiah } from './formatRupiah.js';

document.addEventListener('DOMContentLoaded', () => {
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

    const priceCells = document.querySelectorAll('td[data-price]');
    priceCells.forEach(cell => {
        const rawValue = parseFloat(cell.textContent.replace(/[^\d.,]/g, '').replace(',', '.')) || 0;
        cell.textContent = formatRupiah(rawValue);
    });
});
