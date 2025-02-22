@Library('pipeline-orbill-shared-lib') _

pipeline {
    agent {
        label getGlobalConfigValue('agent_label_linux') // Define o agente com base na configuração global
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: getGlobalConfigValue('keep_log_qty')))
        ansiColor('xterm')
    }

    environment {
        SERVICE_NAME = 'backoffice'

        // Informações sobre o último commit
        IS_LAST_COMMIT_BY_JENKINS = "${getLastCommitterEmail() == getGlobalConfigValue('jenkins_mail_commiter')}"
        IS_LAST_COMMIT_FROM_DEVELOPMENT_PR = "${isLastCommitPullRequest('development')}"

        // Define se é uma build de release se é 'master' e o ultimo commit não foi realizado pelo proprio jenkins
        IS_RELEASE_BUILD = "${(BRANCH_NAME == 'master' && !IS_LAST_COMMIT_BY_JENKINS.toBoolean())}"
           

        // Configuração Maven
        MAVEN_REVISION_PROPERTY = "${getPomRevisionProperty('pom.xml')}"

        // Define versão a ser utilizada no maven e imagens docker
        RELEASE_VERSION_BUILD = """${
            IS_LAST_COMMIT_FROM_DEVELOPMENT_PR.toBoolean()?
            bumpVersion (version: MAVEN_REVISION_PROPERTY, patchLevel: 'MINOR', release: IS_RELEASE_BUILD.toBoolean()):
            MAVEN_REVISION_PROPERTY - '-SNAPSHOT'
        }"""

        VERSION_BUILD = "${IS_RELEASE_BUILD.toBoolean() ? RELEASE_VERSION_BUILD : generateTemporaryVersionName()}"

        // Define repositório para upload da imagem Docker
        REPOSITORY_DEPLOY_IMAGE = "${getDockerRepositoryInternal(IS_RELEASE_BUILD.toBoolean())}"

        // Define se deve haver build de imagens Docker e atualização no registry
         BUILD_DOCKER_IMAGES = "${(BRANCH_NAME ==~ /master|development/ || (BRANCH_NAME ==~ /PR-.*/ && CHANGE_BRANCH?.contains('bugfix')))}"
         PUSH_DOCKER_IMAGES = "${BUILD_DOCKER_IMAGES}"

        // Define TAG da imagem Docker
         IMAGE_APP_TAG = "${REPOSITORY_DEPLOY_IMAGE}/${SERVICE_NAME}-app:${VERSION_BUILD}"

    }

    stages {
        stage('prepare') {
            steps {
                buildName "#${BUILD_NUMBER} : ${GIT_COMMIT.take(5)} (release : ${IS_RELEASE_BUILD.toBoolean() ? VERSION_BUILD : IS_RELEASE_BUILD})"
                buildDescription "Image : ${IMAGE_APP_TAG}"
                setPomRevisionProperty(file: 'pom.xml', revision: VERSION_BUILD)
                downloadMavenRepositoryCache()
            }
        }

        stage('build images') {
            when {
                expression {
                    BUILD_DOCKER_IMAGES.toBoolean()
                }
            }

            steps {
                // Build Docker images
                dockerBuild(
                    dockerfile: "docker/${SERVICE_NAME}.dockerfile", 
                    tag: IMAGE_APP_TAG
                )

                // Scan images with Trivy
                scanTrivy(reportDir: '.trivy-report', images: [IMAGE_APP_TAG])
            }
        }

        stage('push images') {
            when {
                expression {
                    PUSH_DOCKER_IMAGES.toBoolean()
                }
            }

            steps {
                dockerPush(registry: REPOSITORY_DEPLOY_IMAGE, imageTag: IMAGE_APP_TAG)
            }
        }

        stage('publish release') {
            when {
                expression {
                    IS_RELEASE_BUILD.toBoolean()
                }
            }

            environment {
                NEXT_VERSION_BUILD = "${bumpVersion (version: VERSION_BUILD, patchLevel: 'PATCH')}"
            }

            steps {
                // Commit release version
                gitCommit(addFile: 'pom.xml', message: "release version ${VERSION_BUILD}")
                // Tag release version
                gitTagReleaseVersion(VERSION_BUILD)
                // Set next 'SNAPSHOT' version
                setPomRevisionProperty(file: 'pom.xml', revision: NEXT_VERSION_BUILD)
                // Commit next 'SNAPSHOT' version
                gitCommit(addFile: 'pom.xml', message: "next version ${NEXT_VERSION_BUILD}")
                // Push commits and tag
                gitPushAtomic([BRANCH_NAME, "v${VERSION_BUILD}"])
            }
        }
    }
    
    // post{
    //     always{
    //         build(
    //             job: 'ORBILL-MS/tools/version-notify',
    //             parameters: [
    //                 validatingString(name: 'NOTIFY_IMAGE_TAG', value: IMAGE_APP_TAG),
    //                 validatingString(name: 'NOTIFY_BRANCH_NAME', value: BRANCH_NAME),
    //                 validatingString(name: 'NOTIFY_GIT_COMMIT', value: GIT_COMMIT),
    //                 string(name: 'NOTIFY_SERVICE_NAME', value: SERVICE_NAME),
    //                 string(name: 'NOTIFY_CHANGE_BRANCH', value: "${env.CHANGE_BRANCH?.contains('bugfix') ? CHANGE_BRANCH : ''}"),
    //                 string(name: 'NOTIFY_BUILD_URL', value: BUILD_URL),
    //                 string(name: 'NOTIFY_CURRENT_RESULT', value: currentBuild.currentResult)
    //             ],
    //             wait: false, propagate: false
    //         )
    //         deleteDir()
    //     }
    // }
}
