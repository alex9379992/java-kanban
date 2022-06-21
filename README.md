# java-kanban
Добрый день уважаемый *код-ревьюер*. Данное приложение помогает организовать свои дела! Оно хранит в себе простые и сложные задачи. Я назвал ее **ПОМОГАТОР**.Оно состоит из:
1. Главный класс Main, откуда запускается программа.
2. Класс Menu. Это класс в котором реализован главный функционал ветвлений меню.
3. Класс InMemoryTaskManager. Этот класс работает с простыми задачами. Хранит в себе задачи и методы, для работы с ними. Имплементирован от итерфейса TaskManager.
4. Класс InMemoryEpicManager. Этот класс работает со сложными задачами(Эпиками). Хранит в себе сложные задачи и методы для работы с ними. Имплементирован от итерфейса TaskManager.
5. Класс Task. Этот класс хранит в себе экземпляр обьекта для простых задач.
6. Класс Subtask. Этот класс хранит в себе экземпляр обьекта для подзадач. Наследован от класса Task.
7. Кдасс Epic. Этот класс хранит  в себе экземпляр обьекта для сложных задач(Эпиков). В нем хранятся подзадаче в виде HashMap. Унаследован от Task.
8. Класс ID. Этот класс умеет хранить уникальный номер, и умеет работать с ним.
9. Интерфейс TaskManager. Хранит в себе заготовки методов для InMemoryTaskManager и InMemoryEpicManager.
10. Интерфейс HistoryManager. Хранит в себе заготовки методов для InMemoryHistoryManager.
11. Класс InMemoryHistoryManager. Сохраняет в себе список  историей вызовов методов getById().
12. Перечисление Status. Хранит в себе перечисление статусов для задач.
 

