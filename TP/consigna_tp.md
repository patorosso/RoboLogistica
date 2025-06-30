# Consigna del Trabajo Práctico - RoboLogistica

## Descripción General
En una colonia automatizada, los recursos son transportados por una red de robots logísticos que operan dentro del alcance de robopuertos. Estos robots manejan ítems entre diferentes cofres inteligentes, cada uno con un rol específico dentro del sistema logístico.

## Tipos de Cofre

### Cofre de Provisión Activa
- Empuja ítems automáticamente hacia cofres que los solicitan.

### Cofre de Provisión Pasiva
- Almacena ítems disponibles para ser tomados, pero no los ofrece activamente.

### Cofre de Solicitud
- Solicita ítems específicos.
- Los recibe desde proveedores activos, búfer o pasivos, respetando ese orden de prioridad.

### Cofre Intermedio/Búfer
- Solicita ítems como el de solicitud.
- También puede servir como proveedor pasivo para otras solicitudes.

### Cofre de Almacenamiento
- Almacena excedentes que no tienen destino claro.
- Se usa como último recurso.

## Robopuertos
- Definen una zona de cobertura dentro de la cual los robots logísticos pueden operar.
- Cada robopuerto tiene una coordenada fija (x, y) y una distancia máxima de alcance.
- Los cofres conectados a un robopuerto están dentro de su área de cobertura.
- Los robots logísticos solo pueden mover ítems entre cofres cubiertos por al menos un mismo robopuerto, o entre robopuertos conectados.
- Los robots tienen una capacidad de carga limitada y deben priorizar las entregas más cercanas.

## Energía y Batería de los Robots Logísticos

### Unidad de Energía
- Se define una unidad abstracta de energía llamada "Célula".
- Cada robot tiene una capacidad máxima de batería medida en Células (ej: 100).

### Consumo de Energía
- La energía consumida por cada movimiento se calcula con la fórmula:
  ```
  Células necesarias = distanciaEuclídea(cofre_origen, cofre_destino) × factor_de_consumo
  ```
- El factor de consumo puede ser, por ejemplo, 1 célula por unidad de distancia.

### Robopuertos como Estaciones de Recarga
- Los robopuertos también funcionan como estaciones de recarga.
- Un robot puede recargar su batería completamente al pasar por un robopuerto (requiere un turno/ciclo de carga).
- Si un robot no tiene suficiente energía para alcanzar el destino, debe planificar una ruta intermedia pasando por un robopuerto para recargar.
- Si no existe una ruta posible con recargas intermedias, el robot debe cancelar o posponer la entrega.

### Reglas de Operación con Batería
1. **Chequeo antes de salir**: Antes de cada entrega, el robot calcula si tiene batería suficiente para llegar al destino (y volver, si el sistema lo requiere).
2. **Ruta planificada con recargas**: Si no tiene suficiente batería, debe trazar una ruta alternativa que incluya recarga en robopuertos intermedios.
3. **Recarga automática**: Al pasar por un robopuerto, un robot recarga automáticamente su batería.
4. **Planificación energética**: La simulación debe considerar la batería como un recurso limitado al tomar decisiones logísticas.

## Ítems y Operaciones Logísticas

### Características de los Ítems
- El sistema trabaja con ítems diferenciados por tipo (ej: "hierro", "circuito", "motor").
- Cada ítem tiene un nombre o identificador único y puede estar presente en distintas cantidades en los cofres.

### Funciones de los Cofres
Cada cofre puede realizar una de las siguientes funciones, por ítem y cantidad:
- **Ofrecer ítems**: Indicando que posee unidades disponibles para ser retiradas por robots.
- **Solicitar ítems**: Especificando cuántas unidades necesita de un tipo determinado.
- **Almacenar ítems excedentes**: Especialmente en el caso de cofres de almacenamiento.

### Restricciones
- No es posible que un mismo cofre tenga roles distintos para diferentes ítems.
- Los robots deben respetar estos pedidos específicos y transportar únicamente la cantidad necesaria de cada ítem.
- Los robots transportan una cantidad predefinida de ítems en cada viaje (configurable a nivel global).

## Objetivos del Sistema

### Funcionalidades Principales
- Definir una red de cofres inteligentes, robopuertos, y robots logísticos.
- Simular los ciclos de operación en los que los ítems se transfieren de acuerdo con las reglas de prioridad y alcance.
- Registrar los movimientos, distancias y decisiones tomadas en cada ciclo.

## Requisitos

### Diseño e Implementación
1. Diseñar las clases que considere necesarias.
2. Implementar una grilla espacial o sistema de coordenadas para ubicar cofres y robopuertos.
3. Los robots deben:
   - Buscar cofres fuente y destino válidos dentro del alcance.
   - Seleccionar el mejor proveedor según prioridad y distancia.
   - Planificar movimientos dentro de su capacidad de carga.

### Capacidades de la Red Logística
4. La red logística debe ser capaz de:
   - Registrar qué cofres están cubiertos por qué robopuertos.
   - Validar si un movimiento es posible según la cobertura.
   - Priorizar solicitudes más urgentes o accesibles.
   - Permitir ejecutar ciclos de simulación con cambios visibles en la red.

## Estado Inicial y Objetivo del Sistema

### Estado Inicial
- El sistema parte de un estado inicial en el que existen múltiples pedidos de entrega y recepción de ítems sin satisfacer.
- Cada cofre de solicitud o intermedio puede tener cantidades deseadas de ciertos ítems.
- Los cofres proveedores (activos, pasivos o búfer) contienen ítems disponibles para ser distribuidos.

### Objetivo
- El sistema logístico ejecuta ciclos de transferencia hasta alcanzar un estado estable.
- Estado estable: Todos los pedidos han sido completamente satisfechos y no existen ítems en tránsito pendientes.
- Si esto no es posible (por falta de ítems, cobertura de robopuertos, rutas inviables por batería, etc.), el sistema debe detectar la imposibilidad y reportar claramente los pedidos no satisfechos.

## Inicio de Operaciones y Red de Cobertura

### Punto de Partida
- Todos los robots logísticos comienzan sus operaciones desde uno o más robopuertos iniciales, totalmente cargados.
- Cada robot sólo puede operar dentro del alcance de su robopuerto o de aquellos con los que comparta zona de cobertura.

### Cobertura y Accesibilidad
- Los cofres forman parte de la red logística únicamente si están dentro del área de cobertura de al menos un robopuerto.
- Un cofre fuera de toda cobertura se considera inaccesible y no puede participar en la logística.
- Los movimientos entre cofres solo son válidos si existe al menos un camino viable entre ellos, respetando tanto las zonas de cobertura como las limitaciones de batería del robot.

## Carga de Datos desde Archivo

### Formato de Entrada
- Toda la configuración inicial debe ser cargada desde archivo.
- El formato queda a criterio de cada equipo (JSON, CSV, YAML, texto plano estructurado, etc.).
- Debe contener claramente la información necesaria para construir el sistema logístico.

### Información Requerida
- **Red logística**: Ubicación de cada robopuerto, posición y tipo de cada cofre logístico.
- **Contenido de cada cofre**: Ítems disponibles para ofrecer, solicitados y sus cantidades, almacenados (si corresponde).
- **Robots logísticos**: Punto de inicio, capacidad total de batería, capacidad de traslado.

## Glosario

- **Robot logístico**: Unidad móvil que transporta ítems entre cofres. Tiene una batería limitada (medida en Células) y siempre parte inicialmente desde un robopuerto. Planifica rutas eficientes considerando cobertura, consumo de energía y prioridades logísticas.
- **Robopuerto**: Infraestructura fija que define una zona de cobertura dentro de la cual pueden operar los robots. También funciona como estación de recarga. Todo cofre debe estar dentro del alcance de al menos un robopuerto para ser considerado parte de la red.
- **Cofre logístico**: Contenedor inteligente de ítems. Existen distintos tipos con comportamientos y prioridades específicos.
- **Zona de cobertura**: Área definida por un robopuerto. Los cofres dentro de esta zona pueden enviar y recibir ítems mediante robots logísticos. Puede haber zonas superpuestas.
- **Célula (de energía)**: Unidad abstracta que mide la batería de los robots. Cada movimiento consume células proporcionalmente a la distancia recorrida.
- **Estado estable**: Situación en la que todos los pedidos han sido satisfechos y no hay ítems en tránsito. El objetivo de la simulación es alcanzar este estado o informar que no es posible.

## Entrega y Presentación

### Formato de Entrega
- La entrega deberá realizarse en formato PDF, incluyendo los casos de prueba, el informe final y cualquier otro documento relevante.
- El código fuente de la solución se entregará en un archivo comprimido adjunto.
- **Fecha de Entrega**: 3 de Julio de 2025

### Presentación
- Se realizará una breve presentación de la solución desarrollada.
- Mostrar su funcionamiento en diferentes situaciones y destacar sus características principales.
- **Duración estimada**: 10 minutos

## Observaciones
- Si se tuvieran dudas durante el desarrollo de la tarea, consultar con sus docentes para recibir orientación adicional.
- Grupos de 4 o 5 personas. 