builderNode {
  checkout scm
  withCredentials([usernameCredentials(id: "jenkins_artifactory.gameofloans.com", prefix: "ARTIFACTORY")]) {
    sh '''docker run \
      --rm \
      -i \
      -v "$(pwd):/app" \
      -e ARTIFACTORY_USERNAME \
      -e ARTIFACTORY_PASSWORD \
      --workdir=/app \
      docker.gameofloans.com/universe/maven:3.5-jdk-8 \
        bash -c "
        ARTIFACTORY_REPO_RELEASE=\\$(mvn help:evaluate -Dexpression=artifactoryRepositoryRelease -q -DforceStdout) ; \
        ARTIFACTORY_REPO_SNAPSHOT=\\$(mvn help:evaluate -Dexpression=artifactoryRepositorySnapshot -q -DforceStdout) ; \
        mvn deploy \
          -DaltReleaseDeploymentRepository=\\"artifactory-releases::default::\\${ARTIFACTORY_REPO_RELEASE}\\" \
          -DaltSnapshotDeploymentRepository=\\"artifactory-snapshot::default::\\${ARTIFACTORY_REPO_SNAPSHOT}\\" \
        "
    '''
  }
}
