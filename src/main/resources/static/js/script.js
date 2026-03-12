document.addEventListener('DOMContentLoaded', function() {
    // Botones izquierdos
    document.querySelectorAll('.scroll-btn.left').forEach(button => {

        button.addEventListener('click', function(e) {
            e.preventDefault();
            const categoryId = this.dataset.category;

            const container = document.getElementById('scroll-' + categoryId);

            if (container) {
                container.scrollBy({ left: -300, behavior: 'smooth' });
            }
        });
    });
    // Botones derechos
    document.querySelectorAll('.scroll-btn.right').forEach(button => {

        button.addEventListener('click', function(e) {
            e.preventDefault();
            const categoryId = this.dataset.category;

            const container = document.getElementById('scroll-' + categoryId);

            if (container) {
                container.scrollBy({ left: 300, behavior: 'smooth' });
            }
        });
    });
});

// Función para mostrar/ocultar carrito
function toggleCart() {
    const panel = document.getElementById('cartPanel');
    panel.classList.toggle('show');
}

// Cerrar carrito al hacer click fuera
document.addEventListener('click', function(event) {
    const cart = document.querySelector('.cart-floating');
    const panel = document.getElementById('cartPanel');

    if (!cart.contains(event.target) && panel.classList.contains('show')) {
        panel.classList.remove('show');
    }
});

// Guardar posición antes del submit
document.querySelectorAll('.loan-form').forEach(form => {
    form.addEventListener('submit', function() {
        sessionStorage.setItem('scrollPos', window.scrollY);
    });
});

// Restaurar posición después de recargar
window.addEventListener('load', function() {
    const scrollPos = sessionStorage.getItem('scrollPos');
    if (scrollPos) {
        window.scrollTo(0, parseInt(scrollPos));
        sessionStorage.removeItem('scrollPos');
    }
});