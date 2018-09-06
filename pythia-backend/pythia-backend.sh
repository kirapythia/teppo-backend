#! /bin/bash
#
# Super simple wrapper to allow overriding JVM arguments on startup. Controlled by presence of the JAVA_OPTS variable,
# most likely from environment.
#
if [ -z "$JAVA_OPTS" ]; then
  # JAVA_OPTS not set, use sensible defaults
  JAVA_OPTS="-Xmx2G"
fi

# Proper quoting based on Jenkins CI's start script in official Docker image.
eval "exec java $JAVA_OPTS -jar /app/pythia-backend.jar \"\$@\""
