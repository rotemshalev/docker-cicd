job('nodeJS-demo') {
    scm {
        git('git://github.com/rotemshalev/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('NodeJS') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install")
    }
}

job('NodeJS Docker example') {
    scm {
        git('git://github.com/rotemshalev/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('NodeJS') 
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('rotemshalev/docker-cicd') //qa / dev
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            buildContext('/basics')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}