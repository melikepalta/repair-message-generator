# Containerization Summary

- Dockerfile: `/Dockerfile` (multi-stage; uses `maven:3.9.16-eclipse-temurin-25` to build and `eclipse-temurin:25-jre` to run)
- .dockerignore: `/ .dockerignore` (excludes `target`, VCS, and other build artifacts)
- CI workflow: `.github/workflows/docker-build.yml` (builds and pushes to GHCR)

How to verify locally:

```bash
docker buildx build --platform linux/amd64 -t repairmsg:latest .
docker run --rm -p 8080:8080 repairmsg:latest
```

Notes:
- This environment does not have Docker available, so I could not build the image here. Use the CI workflow or run the above commands locally.
- If you want image pushed to a different registry, update the `tags` and credentials in the workflow.

If you'd like, I can:
- Update the Dockerfile to use a different base runtime (distroless or Alpine).
- Add a `healthcheck` and non-root user tuning.
- Generate Kubernetes manifests (`appmod-generate-k8s-manifest`).
