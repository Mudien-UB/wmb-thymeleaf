export function formatRupiah(angka) {
    if (!angka) return 'Rp 0,00';

    let number = parseFloat(angka);
    if (isNaN(number)) return 'Rp 0,00';

    let parts = number.toFixed(2).split('.');
    let integerPart = parts[0];

    let sisa = integerPart.length % 3;
    let rupiah = integerPart.substring(0, sisa);
    let ribuan = integerPart.substring(sisa).match(/\d{3}/g);

    if (ribuan) {
        let separator = sisa ? '.' : '';
        rupiah += separator + ribuan.join('.');
    }

    return 'Rp ' + rupiah ;
}
