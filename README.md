**Organizing.sbFiles**

**Описание**
Программа для обработки входных .sb файлов с информацией о менеджерах и сотрудниках.
Поддерживает сортировку данных, генерацию статистики и вывод в файл или консоль.
Код соответствует требуемому формату вывода, использует только стандартную библиотеку Java.

**Требования**
Java: 17 
Система сборки: не используется, запуск через javac и java
Сторонние библиотеки: отсутствуют, используется только стандартная библиотека JDK

**Сборка и запуск**
Очистить старые сборки:
rm -r -fo out

Скомпилировать проект:
javac -d out src\models\*.java src\services\*.java src\Main.java

Создать JAR:
jar cfe app.jar Main -C out .

Запустить (примеры вариантов):
java -jar app.jar --sort=name --order=asc --stat
java -jar app.jar -s=salary --order=desc --stat -o=file --path=output/statistics.txt
java -jar app.jar --stat
