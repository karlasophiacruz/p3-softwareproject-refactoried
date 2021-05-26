# Sistema de Folha de Pagamento

O objetivo do projeto é construir um sistema de folha de pagamento. O sistema consiste do gerenciamento de pagamentos dos empregados de uma empresa. Além disso, o sistema deve gerenciar os dados destes empregados, a exemplo os cartões de pontos. Empregados devem receber o salário no momento correto, usando o método que eles preferem, obedecendo várias taxas e impostos deduzidos do salário.

 - Alguns empregados são horistas. Eles recebem um salário por hora trabalhada. Eles submetem "cartões de ponto" todo dia para informar o número de horas trabalhadas naquele dia. Se um empregado trabalhar mais do que 8 horas, deve receber 1.5 a taxa normal durante as horas extras. Eles são pagos toda sexta-feira.
 - Alguns empregados recebem um salário fixo mensal. São pagos no último dia útil do mês (desconsidere feriados). Tais empregados são chamados "assalariados".
 - Alguns empregados assalariados são comissionados e portanto recebem uma comissão, um percentual das vendas que realizam. Eles submetem resultados de vendas que informam a data e valor da venda. O percentual de comissão varia de empregado para empregado. Eles são pagos a cada 2 sextas-feiras; neste momento, devem receber o equivalente de 2 semanas de salário fixo mais as comissões do período.
 
	 - Empregados podem escolher o método de pagamento.
	 - Podem receber um cheque pelos correios
	 - Podem receber um cheque em mãos
	 - Podem pedir depósito em conta bancária

 - Alguns empregados pertencem ao sindicato (para simplificar, só há um possível sindicato). O sindicato cobra uma taxa mensal do empregado e essa taxa pode variar entre empregados. A taxa sindical é deduzida do salário. Além do mais, o sindicato pode ocasionalmente cobrar taxas de serviços adicionais a um empregado. Tais taxas de serviço são submetidas pelo sindicato mensalmente e devem ser deduzidas do próximo contracheque do empregado. A identificação do empregado no sindicato não é a mesma da identificação no sistema de folha de pagamento.
 - A folha de pagamento é rodada todo dia e deve pagar os empregados cujos salários vencem naquele dia. O sistema receberá a data até a qual o pagamento deve ser feito e calculará o pagamento para cada empregado desde a última vez em que este foi pago.

# Code Smells

## Long Method

- Em [EmployeeApp](https://github.com/karlasophiacruz/p3-softwareproject/blob/main/payroll/src/app/EmployeeApp.java), métodos [AddEmployee()](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/EmployeeApp.java#L30) e [ChangeEmployee()](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/EmployeeApp.java#L110) muito extensos.
-  Em [AuxApp](https://github.com/karlasophiacruz/p3-softwareproject/blob/main/payroll/src/app/AuxApp.java), método [ChangeSyndicalst()](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/AuxApp.java#L318) muito extenso.
-  Em [PaymentApp](https://github.com/karlasophiacruz/p3-softwareproject/blob/main/payroll/src/app/PaymentApp.java), método [RunPayroll()](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/PaymentApp.java#L93) muito extenso.
-  Em [Employee](https://github.com/karlasophiacruz/p3-softwareproject/blob/main/payroll/src/model/employee/Employee.java), método toString() com decisões lógicas para comportamento.
-  Em [Syndicate](https://github.com/karlasophiacruz/p3-softwareproject/blob/main/payroll/src/model/syndicate/Syndicate.java), método toString() com decisões lógicas para comportamento.

## Generative Speculation

- Métodos getters e setters de inúmeras classes inutilizados.

## Long Parameter List

- Longas chamadas de métodos nos construtores de [Employee](payroll/src/model/employee/Employee.java) e suas subclasses, [Hourly](payroll/src/model/employee/Hourly.java), [Salaried](payroll/src/model/employee/Salaried.java) e [Comissioned](payroll/src/model/employee/Commissioned.java).
- Em [AuxApp](payroll/src/app/AuxApp.java), longo parâmetro no método [AddEmployeeP()](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/AuxApp.java#L102).
  
## Duplicated Code

- Em [PayrollApp](payroll/src/app/PayrollApp.java), método main com condicionais repetidas.
- Em [PaymentApp](payroll/src/app/PaymentApp.java), método [RunPayroll()](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/PaymentApp.java#L93) com várias condicionais com o mesmo código.
- Em [SaleReport](payroll/src/model/employee/SaleReport.java) e [ServiceTax](payroll/src/model/syndicate/ServiceTax.java), há a mesma estruturação de classe.
- Em vários métodos, há a repetição de mensagem de leitura ou erro.

## Data Class

- No pacote payment, todas as classes só possuem o objetivo de armazenar os dados e os métodos getters, setters e toString().

## Long Class

- Em [AuxApp](payroll/src/app/AuxApp.java) e [EmployeeApp](payroll/src/app/EmployeeApp.java), mesmo com a tentativa de separação, há inúmeros métodos.
	

# Refactoried

## Template Method

A definição oficial do padrão Template Method é: “O Padrão Template Method define o esqueleto de um algoritmo dentro de um método, transferindo alguns de seus passos para as subclasses. O Template Method permite que as subclasses redefinam certos passos de um algoritmo sem alterar a estrutura do próprio algoritmo”.

No caso do projeto, foi utilizado o Template Method em [EmployeeApp](payroll/src/app/EmployeeApp.java), no método [ChangeEmployee](https://github.com/karlasophiacruz/p3-softwareproject/blob/e6abd88c28afc19bedeaebc2b9be3c6fe26367af/payroll/src/app/EmployeeApp.java#L110) para resolver o code smells presente.

## Extract Method

Com o intuito de solucionar o code smells Duplicated Code, o Extract Method possui a função de separar as repetições em chamadas do método criado a partir dele e modularizar os métodos longos.

No caso do projeto, foi utilizado o Extract Method em várias partes do projeto, como nas condicionais do [PaymentApp](payroll/src/app/PaymentApp.java) e nas mensagens de erro e de leitura, evitando a duplicação.

## Interpreter Pattern

Com o intuito de filtrar os tipos de empregados e checar se ele é um sindicalista ou não. No caso do projeto, foi criada uma classe [EmployeeLists](payroll/src/app/EmployeeLists.java) para fazer as filtragens e resolver o code smells de repetições.

## Strategy Pattern

Com o intuito de definir uma família de algoritmos, encapsular cada um, e fazê-los intercambiáveis, ele permite que algoritmos variem independentemente entre clientes que os utilizam.

No caso do projeto, foi utilizado o Strategy Pattern para solucionar as repetições e melhorar as refatorações dos tipos de [PaymentSchedule](payroll/src/model/payment/PaymentSchedule.java), ao criar uma [interface](payroll/src/app/scheduleManager/ScheduleType.java) e as classes para cada schedule correspondente ([MonthlySchedule](payroll/src/app/scheduleManager/MonthlySchedule.java), [WeeklySchedule](payroll/src/app/scheduleManager/WeeklySchedule.java), [BiweeklySchedule](payroll/src/app/scheduleManager/BiweeklySchedule.java)).

## Remoction Generative Speculation

No caso do projeto, alguns construtores e getters e setters que não foram utilizados ou inutilizados durante o refatoramento foram excluidos.

## Move Method

No caso do projeto, alguns métodos foram movidos para classes mais próprias, de modo a melhorar a otimização do sistema.
