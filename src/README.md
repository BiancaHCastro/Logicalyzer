# Logic Prover

Biblioteca em Java para análise e avaliação de **Lógica Proposicional**.

O projeto permite montar fórmulas proposicionais, verificar sua estrutura, gerar as possíveis interpretações e construir a tabela-verdade, classificando a fórmula como **tautologia, contradição ou contingência**.

## Tecnologias

* **Java**
* Java Collections Framework
* Execução via **CLI**
* Sem bibliotecas externas

## Como testar

Clone ou abra o projeto na sua IDE Java de preferência.

Execute o arquivo:

```text
src/Main.java
```

O programa será iniciado no terminal e solicitará uma fórmula:

```text
Digite uma formula ou 'sair':
```

Digite a fórmula desejada e pressione `Enter`.

Para encerrar:

```text
sair
```

## Sintaxe

As fórmulas podem utilizar as seguintes proposições:

```text
p
q
r
s
t
```

O projeto permite utilizar até **5 proposições diferentes**.

### Operadores

| Operação            | Símbolo | Exemplo |
| ------------------- | ------- | ------- |
| Negação             | `¬`     | `¬p`    |
| Conjunção           | `∧`     | `p ∧ q` |
| Disjunção           | `∨`     | `p ∨ q` |
| Disjunção exclusiva | `⊕`     | `p ⊕ q` |
| Condicional         | `→`     | `p → q` |
| Bicondicional       | `↔`     | `p ↔ q` |

### Parênteses

Parênteses podem ser utilizados para definir a estrutura da fórmula:

```text
(p ∧ q) → r
```

```text
(p → q) ∧ (q → p)
```

```text
¬(p ∨ q)
```

### Atalhos

Também é possível utilizar alguns símbolos alternativos:

| Operação            | Símbolo principal | Alternativa |   |
| ------------------- | ----------------- | ----------- | - |
| Negação             | `¬`               | `!` ou `~`  |   |
| Conjunção           | `∧`               | `&`         |   |
| Disjunção           | `∨`               | `           | ` |
| Disjunção exclusiva | `⊕`               | `^`         |   |
| Condicional         | `→`               | `->`        |   |
| Bicondicional       | `↔`               | `<->`       |   |

Exemplo:

```text
(p -> q) & (!q | p)
```

## Exemplos

### Tautologia

```text
p ∨ ¬p
```

Resultado esperado:

```text
Classificação: TAUTOLOGIA
```

### Contradição

```text
p ∧ ¬p
```

Resultado esperado:

```text
Classificação: CONTRADIÇÃO
```

### Contingência

```text
p ∧ q
```

Resultado esperado:

```text
Classificação: CONTINGÊNCIA
```

## Estrutura

```text
src/
├── Main.java
│
└── biblioteca/
    ├── Constantes.java
    ├── UnidadeLexica.java
    ├── AnalisadorLexico.java
    ├── AnalisadorSintatico.java
    ├── Interpretacao.java
    ├── GeradorInterpretacoes.java
    ├── Avaliador.java
    ├── ResultadoAnalise.java
    └── Analisador.java
```

## Backlog (TODO)

* [ ] Implementar análise léxica
* [ ] Implementar análise sintática completa de FBF
* [ ] Implementar método de Tableaux
* [ ] Melhorar representação da tabela-verdade
* [ ] Criar interface Web
* [ ] Disponibilizar a biblioteca por meio de uma API
* [ ] Preparar deploy em infraestrutura Web
