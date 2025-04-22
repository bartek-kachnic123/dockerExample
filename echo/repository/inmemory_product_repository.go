package repository

import (
	. "echo/model"
	"errors"
	"sync"
)

var (
	ErrNotFound = errors.New("product not found")
)

type InMemoryProductRepository struct {
	Products []Product
	mu       sync.Mutex
}

func NewInMemoryProductRepository() *InMemoryProductRepository {
	return &InMemoryProductRepository{
		Products: []Product{},
		mu:       sync.Mutex{},
	}
}

func (r *InMemoryProductRepository) AddProduct(product Product) Product {
	r.mu.Lock()
	defer r.mu.Unlock()

	r.Products = append(r.Products, product)

	return product
}

func (r *InMemoryProductRepository) GetAllProducts() []Product {
	r.mu.Lock()
	defer r.mu.Unlock()

	productsCopy := make([]Product, len(r.Products))
	copy(productsCopy, r.Products)
	return productsCopy
}

func (r *InMemoryProductRepository) GetProductById(id int) (*Product, error) {
	r.mu.Lock()
	defer r.mu.Unlock()

	for i := range r.Products {
		if r.Products[i].ID == id {
			return &r.Products[i], nil
		}
	}

	return nil, ErrNotFound
}

func (r *InMemoryProductRepository) UpdateProductById(id int, updatedProduct Product) (*Product, error) {
	r.mu.Lock()
	defer r.mu.Unlock()

	for i := range r.Products {
		if r.Products[i].ID == id {
			r.Products[i].Name = updatedProduct.Name
			r.Products[i].Price = updatedProduct.Price

			return &r.Products[i], nil
		}
	}

	return nil, ErrNotFound
}

func (r *InMemoryProductRepository) DeleteProductById(id int) error {
	r.mu.Lock()
	defer r.mu.Unlock()

	for i := range r.Products {
		if r.Products[i].ID == id {
			r.Products = append(r.Products[:i], r.Products[i+1:]...)
			return nil
		}
	}

	return ErrNotFound
}
