📦 DISTRISOFT – Sistema de Gestión para Proveedores y Panaderías

Cada día, cientos de panaderías y pequeños comercios necesitan insumos frescos, pero el proceso de compra sigue siendo lento, manual y desorganizado. Distrisoft nace como una plataforma tecnológica para conectar negocios locales con proveedores de forma rápida, confiable y digital.
Porque pedir insumos no debería ser más complicado que pedir un domicilio 😄.


🧩 1. Contexto del Problema

Los pequeños negocios enfrentan múltiples dificultades:

📞 Dependencia de llamadas o mensajes informales.

📃 Procesos manuales (libretas, notas en papel, etc.).

📦 Falta de control sobre inventarios y compras.

⏳ Tiempo perdido buscando proveedores confiables.

💸 Falta de claridad en precios, presentaciones y disponibilidad.

Distrisoft busca eliminar estas barreras, modernizando el flujo de compras sin necesidad de conocimientos técnicos avanzados.


🚀 2. Solución Propuesta

Desarrollar un módulo digital (web/móvil) que funcione como plataforma intermediaria entre panaderías y proveedores, permitiendo gestionar productos, pedidos y entregas desde un mismo lugar.

⭐ Funciones principales del sistema

🛍️ Catálogo de productos: precios, presentaciones, disponibilidad.

🧾 Creación y gestión de pedidos (selección, cantidades, observaciones).

🚚 Programación de entregas.

📊 Control de inventario básico con alertas de bajo stock.

📜 Historial de compras.

💳 (Opcional) Registro de pagos o integración con pasarelas.

🔐 Roles y seguridad básica (administrador, negocio, proveedor).


📝 3. Requerimientos Funcionales (RF)

RF1. Registrar negocio: nombre, dirección, teléfono.
RF2. Registrar proveedor: nombre, contacto, catálogo asociado.
RF3. Crear pedido en estado EN_CREACION.
RF4. Agregar productos al pedido (producto, cantidad, presentación).
RF5. Usar el precio vigente del producto según su presentación.
RF6. Calcular el total bruto del pedido (sumatoria de cada ítem).
RF7. Aplicar descuento automático si el proveedor lo ofrece.
RF8. Confirmar pedido → estado CONFIRMADO; luego no se puede editar.
RF9. Validar cantidades > 0 y valores no negativos.
RF10. Mostrar resumen del pedido (proveedor, ítems, precios, total, descuento, fecha estimada).
RF11. Guardar historial del negocio para futuras compras rápidas.
RF12. Permitir actualizar catálogo del proveedor (precios, disponibilidad).


🔧 4. Reglas del Negocio

📦 Catálogo por proveedor: cada proveedor maneja sus propios productos y precios.

🏷️ Descuentos opcionales: por monto mínimo o cantidad por producto.

🔄 Estados del pedido:

PENDIENTE

EN_PROCESO

ENVIADA

ENTREGADA

CANCELADA

🛑 No se puede editar un pedido después de confirmado.

📉 No se manejan inventarios avanzados ni pagos en efectivo en esta versión.

💻 No requiere base de datos obligatoria en fase inicial (puede simularse en memoria).


🧪 5. Criterios de Aceptación (Given / When / Then)

CA1. Precios:
Dado un producto seleccionado, cuando se agrega al pedido, entonces el precio aplicado es el del catálogo del proveedor.

CA2. Descuento automático:
Dado un total bruto superior al mínimo requerido, cuando se calcula el total final, entonces se aplica el descuento correspondiente.

CA3. Bloqueo de edición:
Dado un pedido CONFIRMADO, cuando intento agregar o eliminar productos, entonces el sistema lo rechaza.

CA4. Validación de cantidades:
Dado que ingreso cantidad 0 o negativa, cuando agrego el producto, entonces se rechaza.

CA5. Resumen del pedido:
Dado un pedido válido, cuando solicito el resumen, entonces se muestran productos, precios, subtotal, descuentos y total final.


🧱 6. Diseño del Sistema 

1. Cliente 

Entidad que identifica a cada cliente del sistema. Incluye nombre, teléfono y dirección. Cada cliente recibe un ID autogenerado para su registro y gestión.

2. DetalleOrden

Representa un ítem dentro de una orden. Guarda el producto del catálogo, la cantidad solicitada y calcula el subtotal según el precio actual del ítem.

3. ItemCatalogo

Representa un producto del catálogo con su ID, nombre, precio y stock disponible.

4. Orden

Contiene el cliente, los ítems del pedido, el estado, la fecha y el total calculado a partir de sus detalles.

5. EstadoOrden (enum)

PENDIENTE, EN_PROCESO, ENVIADA, ENTREGADA, CONCELADA.

6. DistrisoftService

Coordina todo el sistema: administra clientes, catálogo y órdenes, actualiza el stock, cambia estados y maneja la persistencia en archivos .dat. 


## 👨‍🎨 Equipo / Autor

Proyecto analizado y documentado por:  

Keiner Josue Barbosa Calderon - 192502
Maria Laura Contreras Trillos - 192521
Paula Andrea Echavez Vargas - 192487

---

> “La clave no es solo vender productos, sino conectar necesidades con soluciones.” ✨

![Logo]("C:\Users\LENOVO\OneDrive\Desktop\Proyecto_Java_2\assets\UML.png")
