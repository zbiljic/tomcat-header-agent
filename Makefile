SHELL = bash

default: help

.PHONY: checkscripts
checkscripts: ## Lint shell scripts
	@echo "==> Linting scripts..."
	@shellcheck ./scripts/*.sh

.PHONY: package
package: ## Package Java project
	@mvn package

.PHONY: verify
verify: ## Verify Java project
	@mvn verify

.PHONY: pack
pack: package ## Build Docker image
	@docker build -t zbiljic/tomcat-tha .

.PHONY: run
run: ## Run Docker image
	@docker run --rm -p 8888:8080 zbiljic/tomcat-tha

.PHONY: clean
clean: ## Remove build artifacts
	@echo "==> Cleaning build artifacts..."
	@mvn clean
	@docker rmi zbiljic/tomcat-tha

HELP_FORMAT="    \033[36m%-15s\033[0m %s\n"
.PHONY: help
help: ## Display this usage information
	@echo "Valid targets:"
	@grep -E '^[^ ]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		sort | \
		awk 'BEGIN {FS = ":.*?## "}; \
			{printf $(HELP_FORMAT), $$1, $$2}'
	@echo

FORCE:
