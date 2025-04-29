import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/products': 'http://localhost:9000',
      '/categories': 'http://localhost:9000',
      '/carts': 'http://localhost:9000',
    }
  }
})
