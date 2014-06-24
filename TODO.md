Mercurio next steps

API Front:
	· Receiver
		+ void recibirEvento(Evento evento)
	· Dispatcher
		+ List<Mensaje> generarAlertas()

IMPL secuencial

· ReceiverSequentialImpl(DispatcherSequentialImpl)
recibirEvento --> GestorAlertas.recibirEvento

· DispatcherSequentialImpl
+ setAlerta
generarAlertas() --> GestorAlertas.generarAlertas(this.getAlerta())

IMPL Multi hilo

· ReceiverExecutorServiceImpl(QueueEventos)
recibirEvento --> QueueEventos

Callable ==> QueueEventos -> GestorAlertas.recibirEvento -> Alerta -> QueueAlertas

· DispatcherExecutorServiceImpl(QueueAlertas)
generarAlertas() ==> QueueAlertas -> GestorAlertas.generarAlertas(...)

IMPL DISRUPTOR

The same but with Reactor instead of Queues.

· ReceiverDisruptorImpl(QueueEventos)
recibirEvento --> QueueEventos

Callable ==> QueueEventos -> GestorAlertas.recibirEvento -> Alerta -> QueueAlertas

· DispatcherDisruptorImpl(DisruptorAlertas)
generarAlertas() ==> QueueAlertas -> GestorAlertas.generarAlertas(...)



