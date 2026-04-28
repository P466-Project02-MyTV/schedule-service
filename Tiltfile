# Build
custom_build(
    # Name of the container image
    ref = 'schedule-service',
    # Command to build the container image
    command = 'gradlew.bat bootBuildImage --imageName ghcr.io/p466-project02-mytv/schedule-service:latest',
    # Files to watch that trigger a new build
    deps = ['build.gradle', 'src']
)

# Deploy
k8s_yaml(['k8s/deployment.yaml', 'k8s/service.yaml'])

# Manage
k8s_resource('schedule-service', port_forwards=['9001'])