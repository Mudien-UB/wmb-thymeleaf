import { rupiahFormat } from './rupiah-format.js';

document.addEventListener('DOMContentLoaded', () => {
    const formattedInput = document.getElementById('priceFormatted');
    const rawInput = document.getElementById('priceRaw');

    if (!formattedInput || !rawInput) return;

    if (rawInput.value) {
        const number = parseInt(rawInput.value) || 0;
        formattedInput.value = rupiahFormat(number);
    }

    formattedInput.addEventListener('input', function () {
        const numberString = this.value.replace(/[^0-9]/g, '');
        const number = parseInt(numberString) || 0;

        rawInput.value = number;
        this.value = rupiahFormat(number);
    });

    formattedInput.addEventListener('blur', function () {
        const numberString = this.value.replace(/[^0-9]/g, '');
        const number = parseInt(numberString) || 0;

        rawInput.value = number;
        this.value = rupiahFormat(number);
    });
});
