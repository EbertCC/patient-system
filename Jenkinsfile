// ============================================================
//  Jenkinsfile — Pipeline CI/CD (esqueleto)
//  Proyecto: patient-system (arquitectura modular DDD)
//  Etapas: Build, Análisis Estático, Unitarias, Funcionales,
//          Performance, Seguridad, Gestión de Issues.
// ============================================================

pipeline {
    agent any

    tools {
        // El nombre 'Maven3' debe coincidir con el configurado en
        // Manage Jenkins > Tools. Ajustar si el tuyo se llama distinto.
        maven 'Maven-3.9.14'
        jdk 'JDK21'
    }

    environment {
        SONAR_HOST = 'http://localhost:9000'
        // El token de Sonar se recomienda guardarlo en Jenkins Credentials,
        // no en texto plano. Ver "Credentials" en el paso a paso.
    }

    stages {

        // a. CONSTRUCCIÓN AUTOMÁTICA ----------------------------------
        stage('Build') {
            steps {
                echo '== a. Construcción automática con Maven =='
                bat 'mvn -B clean compile'
                // En Linux/Mac usar:  sh 'mvn -B clean compile'
            }
        }

        // c. PRUEBAS UNITARIAS ----------------------------------------
        stage('Unit Tests') {
            steps {
                echo '== c. Pruebas unitarias con JUnit =='
                bat 'mvn -B test'
            }
            post {
                always {
                    // Publica el reporte de pruebas en Jenkins
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        // b. ANÁLISIS ESTÁTICO ----------------------------------------
        stage('Static Analysis') {
            steps {
                echo '== b. Análisis estático con SonarQube =='
                // Requiere el SonarScanner disponible en el PATH,
                // o configurado en Manage Jenkins > Tools.
                bat "mvn -B sonar:sonar -Dsonar.host.url=${SONAR_HOST} -Dsonar.token=squ_254e216bd6d1069258b273084d73e88ab4b731bd"
            }
        }

        // d. PRUEBAS FUNCIONALES --------------------------------------
        stage('Functional Tests') {
            steps {
                echo '== d. Pruebas funcionales (Postman/Newman o Selenium) =='
                // Opción Postman con Newman (si exportas tu colección):
                //   bat 'newman run postman/patient-system.postman_collection.json'
                // Opción Selenium: los tests E2E de src/test (paquete e2e).
                echo 'Ejecutar colección Postman/Newman o pruebas Selenium E2E.'
            }
        }

        // e. PRUEBAS DE PERFORMANCE -----------------------------------
        stage('Performance Tests') {
            steps {
                echo '== e. Pruebas de performance con JMeter =='
                // Ejemplo (requiere JMeter instalado y un plan .jmx):
                //   bat 'jmeter -n -t performance/plan.jmx -l results.jtl'
                echo 'Ejecutar plan de pruebas JMeter (esqueleto).'
            }
        }

        // f. PRUEBAS DE SEGURIDAD -------------------------------------
        stage('Security Tests') {
            steps {
                echo '== f. Pruebas de seguridad con OWASP ZAP =='
                // Ejemplo (requiere ZAP en modo daemon apuntando a la app):
                //   bat 'zap.bat -cmd -quickurl http://localhost:8085 -quickout zap-report.html'
                echo 'Ejecutar escaneo OWASP ZAP contra http://localhost:8085 (esqueleto).'
            }
        }

        // g. GESTIÓN DE ISSUES ----------------------------------------
        stage('Issue Management') {
            steps {
                echo '== g. Gestión de issues con GitHub Issues =='
                // Los issues se gestionan en GitHub. Los commits enlazan
                // con "Fix #n" para cerrarlos automáticamente al hacer merge.
                echo 'Issues gestionados en GitHub Issues + GitHub Projects.'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completado correctamente.'
        }
        failure {
            echo '❌ El pipeline falló. Revisar la etapa marcada en rojo.'
        }
        always {
            echo 'Fin del pipeline patient-system.'
        }
    }
}
