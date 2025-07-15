import { formatRupiah } from './formatRupiah.js';

document.addEventListener('DOMContentLoaded', () => {
    const formattedInput = document.getElementById('priceFormatted');
    const rawInput = document.getElementById('priceRaw');

    if (!formattedInput || !rawInput) return;

    // Prefill on load (e.g. edit page)
    if (rawInput.value) {
        const number = parseInt(rawInput.value) || 0;
        formattedInput.value = formatRupiah(number);
    }

    formattedInput.addEventListener('input', function () {
        const numberString = this.value.replace(/[^0-9]/g, '');
        const number = parseInt(numberString) || 0;

        rawInput.value = number;
        this.value = formatRupiah(number);
    });

    formattedInput.addEventListener('blur', function () {
        const numberString = this.value.replace(/[^0-9]/g, '');
        const number = parseInt(numberString) || 0;

        rawInput.value = number;
        this.value = formatRupiah(number);
    });
});
