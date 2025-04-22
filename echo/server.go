package main

import (
	. "echo/controller"
	. "echo/model"
	. "echo/repository"
	"github.com/labstack/echo/v4"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"os"
)

func main() {
	e := echo.New()

	databaseURL := os.Getenv("DATABASE_URL")
	if databaseURL == "" {
		e.Logger.Fatalf("DATABASE_URL environment variable not set")
	}

	db, err := gorm.Open(postgres.Open(databaseURL), &gorm.Config{})
	if err != nil {
		e.Logger.Fatalf("Failed to connect to database: %v", err)
	}

	err = db.AutoMigrate(&Product{})
	if err != nil {
		e.Logger.Fatalf("Failed to auto-migrate database schema: %v", err)
	}

	//productRepo := NewInMemoryProductRepository()
	dbProductRepo := NewPostgresProductRepository(db)

	productController := NewProductController(dbProductRepo)

	SetupProductRoutes(e, productController)

	e.Logger.Fatal(e.Start("localhost:1323"))
}
