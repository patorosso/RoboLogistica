# Roadmap de Implementación - TP RoboLogistica

## Lista de Pasos Ordenados

### ✅ PASO 1: Lectura y Validación de Datos Iniciales
- [x] Leer archivos JSON (cofres, robopuertos, robots)
- [x] Construir objetos de dominio a partir de los datos
- [x] Generar redes logísticas usando Union-Find
- [x] Asignar cofres a redes según cobertura de robopuertos
- [x] Asignar robots a redes
- [x] Generar aristas entre robopuertos y cofres

### ✅ PASO 2: Implementar Lógica de Batería y Energía
- [x] Agregar campos de batería a Robot (ya existen en JSON: bateriaMaxima, bateriaActual, factorConsumo)
- [x] Implementar cálculo de consumo de energía por distancia
- [x] Implementar lógica de recarga en robopuertos
- [x] Crear método para verificar si un robot puede completar una ruta

### ✅ PASO 3: Implementar Matrices de Distancias por Robot
- [x] Agregar Map<String, Double> distanciasARobopuertos a Robot
- [x] Agregar Map<String, Double> distanciasACofres a Robot
- [x] Implementar método calcularMatrizDistancias() en Robot
- [x] Calcular matrices al asignar robots a redes

### ✅ PASO 4: Caminos Mínimos en la Red Logística
- [x] Implementar algoritmo Floyd-Warshall para obtener caminos mínimos entre todos los nodos (robopuertos y cofres)
- [x] Guardar matriz de distancias mínimas y predecesores en RedLogistica
- [x] Adaptar lógica de asignación para usar caminos reales del grafo

### ✅ PASO 5: Asignación de Solicitudes
- [x] Buscar mejor proveedor para cada solicitud
- [x] Evaluar robots disponibles usando caminos reales y simulando batería/recarga
- [x] Asignar el robot más eficiente a cada solicitud
- [x] Imprimir la asignación y el camino recorrido

### ✅ PASO 7: Limpieza y Optimización
- [x] Eliminar métodos y campos obsoletos en Robot y RedLogistica
- [x] Mantener solo la lógica basada en caminos mínimos y simulación real

### 🔄 PASO 8: Métodos de Control de Simulación
- [x] Implementar y mantener método esProcesable() en RedLogistica
- [x] Implementar y mantener método getPeticiones() en RedLogistica

### 🔄 PASO 9: Ciclo Principal y Lógica de Simulación
- [x] Procesar peticiones (ya cubierto por asignarSolicitudes en RedLogistica)
- [ ] Crear ciclo principal de simulación en Main usando esProcesable y getPeticiones
- [ ] Validar que todos los cofres críticos sean accesibles durante la simulación
- [ ] Implementar detección de estado estable
- [ ] Manejar casos de bloqueo o imposibilidad

### ❌ PASO 7: Testing y Validación
- [ ] Crear casos de prueba simples
- [ ] Validar funcionamiento con datos de ejemplo
- [ ] Probar casos límite y de error

---

## Estructura de Datos y Algoritmos

### Estructura Actual (Mantener)
```java
// Los cofres ya contienen las solicitudes:
{
  "itemsSolicitados": [{ "nombre": "hierro", "cantidad": 10 }],
  "itemsOfrecidos": [],
  "itemsAlmacenados": []
}

// Los robots ya tienen campos de batería:
{
  "bateriaMaxima": 50,
  "bateriaActual": 50,
  "factorConsumo": 1
}
```

### Estructura a Agregar
```java
// En Robot:
class Robot {
    // Campos existentes...
    private Map<String, Double> distanciasARobopuertos;
    private Map<String, Double> distanciasACofres;
    
    public void calcularMatrizDistancias(List<Robopuerto> robopuertos, List<Cofre> cofres);
    public boolean puedeCompletarRuta(String cofreOrigen, String cofreDestino);
    public double calcularCostoRuta(String cofreOrigen, String cofreDestino);
    public void ejecutarMovimiento(String cofreOrigen, String cofreDestino);
}
```

### Algoritmo de Asignación
```java
// Para cada solicitud (en cofres):
1. Buscar proveedor según prioridad (PA > PP > Buffer > Almacenamiento)
2. Para cada robot disponible:
   a. Calcular ruta: Robot → Robopuerto → Cofre_Origen → Robopuerto → Cofre_Destino
   b. Verificar batería suficiente (usando matriz precalculada)
   c. Calcular costo total (distancia + factor_batería + prioridad)
3. Seleccionar robot con menor costo
4. Ejecutar movimiento y actualizar estados
```

---

## Estado Actual del Proyecto

### ✅ Implementado
- [x] Lectura de archivos JSON (cofres, robopuertos, robots)
- [x] Estructura de clases base (Cofre, Robot, Robopuerto, RedLogistica)
- [x] Generación de redes logísticas usando Union-Find
- [x] Asignación de cofres a redes según cobertura de robopuertos
- [x] Asignación de robots a redes
- [x] Generar aristas entre robopuertos y cofres
- [x] Estructura de grafo para modelar conexiones

### 🔄 En Progreso
- [ ] Lógica de simulación de ciclos
- [ ] Procesamiento de peticiones

### ❌ Pendiente
- [ ] Validación de accesibilidad de cofres críticos
- [ ] Lógica de batería y consumo de energía
- [ ] Algoritmo de asignación robot-solicitud
- [ ] Registro de movimientos
- [ ] Detección de estado estable
- [ ] Tests y casos de prueba

---

## Estructura de Código Actual

### Clases Principales
- **RedLogistica**: Maneja la red logística, nodos, aristas y robots
- **GeneradorDeRedes**: Construye redes usando Union-Find
- **Nodo**: Envuelve entidades (Cofre, Robopuerto) para el grafo
- **Arista**: Representa conexiones entre nodos con peso (distancia)
- **UnionFind**: Algoritmo para encontrar componentes conexas

### Flujo Actual
1. Leer robopuertos y construir redes logísticas
2. Leer cofres y asignarlos a redes según cobertura
3. Leer robots y asignarlos a redes
4. Generar aristas entre robopuertos y cofres
5. (Pendiente) Procesar peticiones y simular movimientos

---

## Observaciones Técnicas

### Decisiones de Diseño
- Uso de Union-Find para identificar redes logísticas
- Grafo no dirigido para modelar conexiones
- Separación de responsabilidades entre clases
- Uso de streams y programación funcional

### Puntos de Atención
- La lógica de batería no está implementada
- Los tipos de cofres no están diferenciados
- Falta validación de accesibilidad
- El procesamiento de peticiones está incompleto

---

*Este roadmap se actualiza con cada avance del proyecto.* 