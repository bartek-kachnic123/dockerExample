describe('Payments', () => {
    const mockProducts = [
        { id: 1, name: 'Produkt A', price: 20 },
        { id: 2, name: 'Produkt B', price: 30 },
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
            .click();
        cy.contains('li', 'Produkt B')
            .contains('button', 'Dodaj do koszyka')
            .click();

        cy.get('nav').contains('Koszyk').click();
    });

    it('create cart', () => {
        cy.intercept('POST', '/carts', (req) => {
            const cart = req.body
            expect(cart.productIds).to.deep.equal([1, 2]);
            expect(cart.id).to.be.a('number');
            req.reply({
                delay: 100,
            });
        }).as('createCart');

        cy.contains('button', 'Złóż zamówienie').click();

        cy.contains('button', 'Przetwarzanie...').should('be.disabled');
        cy.wait('@createCart');
        cy.contains('button', 'Złóż zamówienie').should('not.be.disabled');
    });

    it('should handle empty cart', () => {
        cy.intercept('POST', '/carts', (req) => {
            const cart = req.body
            expect(cart.productIds).to.deep.equal([]);
            expect(cart.id).to.be.a('number');
            req.reply({
                delay: 100,
            });
        }).as('createEmptyCart');

        cy.contains('button', 'Usuń jeden').click();
        cy.contains('button', 'Usuń jeden').click();

        cy.contains('button', 'Złóż zamówienie').click();

        cy.contains('button', 'Przetwarzanie...').should('be.disabled');
        cy.wait('@createEmptyCart');
        cy.contains('button', 'Złóż zamówienie').should('not.be.disabled');
    });
});
