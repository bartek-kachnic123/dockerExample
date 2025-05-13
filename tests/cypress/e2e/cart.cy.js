describe('Cart', () => {
    const mockProducts = [
        { id: 1, name: 'Produkt A', price: 10 },
    ];

    beforeEach(() => {
        cy.intercept('GET', '/products', {
            statusCode: 200,
            body: mockProducts,
        }).as('getProducts');

        cy.visit('/');
        cy.wait('@getProducts');

        cy.contains('li', 'Produkt A')
            .contains('button', 'Dodaj do koszyka')
            .click()
            .click();

        cy.get('nav').contains('Koszyk').click();
        cy.url().should('include', '/cart');
    });

    it('displays products in cart table with correct pricing and total', () => {
        cy.contains('h2', 'Koszyk').should('exist');

        cy.get('table').should('exist');
        cy.get('tbody tr').should('have.length', 1);

        cy.get('tbody tr').within(() => {
            cy.get('td').eq(0).should('contain', 'Produkt A');
            cy.get('td').eq(1).should('contain', '10 PLN');
            cy.get('td').eq(2).should('contain', '2');
            cy.get('td').eq(3).should('contain', '20 PLN');
            cy.get('td').eq(4).contains('Usuń jeden');
        });

        cy.contains('Całkowita kwota: 20 PLN').should('exist');
    });

    it('removes items one by one and updates UI', () => {
        cy.get('table tbody tr').within(() => {
            cy.contains('td', '2');
            cy.contains('td', '20 PLN');
        });
        cy.contains('Całkowita kwota: 20 PLN');

        cy.contains('button', 'Usuń jeden').click();

        cy.get('table tbody tr').within(() => {
            cy.contains('td', '1');
            cy.contains('td', '10 PLN');
        });
        cy.contains('Całkowita kwota: 10 PLN');

        cy.contains('button', 'Usuń jeden').click();

        cy.get('table').should('not.exist');
        cy.contains('Nie wybrano żadnych produktów.').should('exist');
        cy.contains('Całkowita kwota: 0 PLN');
    });
});
