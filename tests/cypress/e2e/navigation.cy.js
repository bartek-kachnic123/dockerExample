describe('Navigation Test', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should have navigation links to Produkty and Koszyk', () => {
    cy.get('nav').within(() => {
      cy.contains('a', 'Produkty')
          .should('have.attr', 'href', '/');
      cy.contains('a', 'Koszyk')
          .should('have.attr', 'href', '/cart');
    });
  });

  it('navigates to Produkty and shows correct heading', () => {
    cy.get('nav').contains('Produkty').click();
    cy.url().should('eq', `${Cypress.config().baseUrl}/`);
    cy.contains('h2', 'Lista produktów').should('exist');
  });

  it('navigates to Koszyk and shows correct heading', () => {
    cy.get('nav').contains('Koszyk').click();
    cy.url().should('include', '/cart');
    cy.contains('h2', 'Koszyk').should('exist');
  });
});
