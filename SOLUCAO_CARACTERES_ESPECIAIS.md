# Solução para Caracteres Especiais (Acentos e Cedilha) no Android Studio

## ✅ Configurações Aplicadas

Já apliquei as seguintes configurações ao seu projeto:

### 1. **Arquivo `.idea/encodings.xml` criado**
- Força o uso de UTF-8 em todo o projeto
- Localização: `.idea/encodings.xml`

### 2. **Gradle configurado (`gradle.properties`)**
- Já estava correto: `org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8`

### 3. **Build.gradle.kts atualizado**
- Adicionada configuração explícita de encoding para tarefas Kotlin

## 🔍 Verificação

Os arquivos do projeto já estão em UTF-8:
```bash
$ file -i BeneficiariesView.kt
text/plain; charset=utf-8
```

## 🛠️ Passos para Resolver no Android Studio

### Opção 1: Configurações do Editor (RECOMENDADO)

1. **Abra o Android Studio**

2. **Vá para Settings/Preferences:**
   - Linux/Windows: `File` → `Settings`
   - Mac: `Android Studio` → `Preferences`

3. **Configure o Encoding:**
   - Navegue para: `Editor` → `File Encodings`
   - Configure todas as opções para **UTF-8**:
     - **Global Encoding:** UTF-8
     - **Project Encoding:** UTF-8
     - **Default encoding for properties files:** UTF-8
     - Marque: ☑️ **Transparent native-to-ascii conversion**

4. **Aplique e Reinicie o IDE**

### Opção 2: Verificar o Teclado no Linux

Se o problema persistir, pode ser do layout do teclado:

1. **Verifique o layout do teclado:**
   ```bash
   setxkbmap -query
   ```

2. **Configure para Português (se necessário):**
   ```bash
   setxkbmap pt
   ```

### Opção 3: Configurações do Sistema (Ubuntu/Debian)

1. Abra `Settings` → `Region & Language`
2. Certifique-se de que o idioma está configurado para Português
3. Em `Input Sources`, adicione "Portuguese (Brazil)" ou "Portuguese"

## 🧪 Teste

Após aplicar as configurações, teste digitando:
- **Acentos:** á, é, í, ó, ú, â, ê, ô, à, ã, õ
- **Cedilha:** ç
- **Maiúsculas:** Á, É, Ç, Ã

Exemplo de código para testar:
```kotlin
// Função para adicionar beneficiários
fun adicionarBeneficiário() {
    val descrição = "Informação sobre o beneficiário"
    val ação = "Ação executada com sucesso"
}
```

## 🔄 Sincronizar o Projeto

Após fazer as alterações:

1. **Invalide o cache:**
   - `File` → `Invalidate Caches...`
   - Marque todas as opções
   - Clique em `Invalidate and Restart`

2. **Sincronize o Gradle:**
   - Clique no ícone de elefante (Gradle) no topo direito
   - Ou: `File` → `Sync Project with Gradle Files`

## 📱 Teste no Emulador/Dispositivo

Se os caracteres aparecem corretamente no código mas não no app:

1. Verifique o locale do dispositivo Android
2. Certifique-se de que as strings estão no arquivo correto:
   - `app/src/main/res/values/strings.xml` (padrão)
   - `app/src/main/res/values-pt/strings.xml` (português)

## ⚠️ Problemas Comuns

### Problema: Caracteres aparecem como "?" ou "�"
**Solução:** O arquivo não está em UTF-8
```bash
# Converter arquivo para UTF-8
iconv -f ISO-8859-1 -t UTF-8 arquivo.kt -o arquivo_utf8.kt
```

### Problema: Não consigo digitar acentos
**Solução:** Problema de layout de teclado do sistema operacional
- Configure o teclado para Português no sistema

### Problema: Caracteres funcionam no código mas não no app
**Solução:** Problema de recursos Android
- Use `strings.xml` para todas as strings visíveis ao usuário
- Configure o locale do app corretamente

## 📋 Checklist Final

- ✅ `.idea/encodings.xml` criado (UTF-8)
- ✅ `gradle.properties` com `-Dfile.encoding=UTF-8`
- ✅ `build.gradle.kts` configurado
- ⬜ Android Studio → Settings → File Encodings (UTF-8)
- ⬜ Reiniciar o Android Studio
- ⬜ Invalidar cache e sincronizar Gradle
- ⬜ Testar digitação de caracteres especiais

## 🎯 Resultado Esperado

Após aplicar todas as configurações, você deve conseguir:
- ✅ Digitar á, é, í, ó, ú, ã, õ, ç normalmente
- ✅ Ver os caracteres corretamente no editor
- ✅ Ver os caracteres corretamente no app compilado
- ✅ Não ter problemas com codificação em Git

---

**Nota:** Se após seguir todos os passos o problema persistir, verifique se está usando um teclado virtual ou alguma ferramenta de entrada especial que possa estar interferindo.

