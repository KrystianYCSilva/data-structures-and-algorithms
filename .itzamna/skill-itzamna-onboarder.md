# Itzamna Onboarder

Source: .itzamna/ + .context/ templates + .github files

Description:
Verify Itzamna artifacts (.itzamna/, .context/, MEMORY.md) and prepare human-approved drafts. Use when: starting a session or onboarding the assistant.

What it teaches
- How to check and safely propose changes to Itzamna-related files while respecting rules (never overwrite CLI files, human gate for .context/ and .itzamna/memory.md).

Instructions
1. Verify presence of:
   - .itzamna/kernel.md, .itzamna/constitution.md, .itzamna/memory.md, .itzamna/templates/
   - .context/project.md, .context/tech.md, .context/rules.md
   - MEMORY.md at repo root
2. If CLI root files exist (GEMINI.md, AGENTS.md, CLAUDE.md) and do not reference Itzamna, prepare an append-only Itzamna Protocol snippet and ask user for approval before appending.
3. If .context/ files contain placeholders, propose drafts (show diffs) and ask for approval.
4. If .itzamna/memory.md is templated, propose an initial short-term memory draft and request approval.
5. NEVER write without explicit human approval.

Examples
- Proposal flow:
  - "I found GEMINI.md without Itzamna reference. Approve append?" -> user approves -> append.

References
- .itzamna/templates/cli-compatibility.md
- .github/ITZAMNA.md
- docs/PROJECT_ROADMAP.md
